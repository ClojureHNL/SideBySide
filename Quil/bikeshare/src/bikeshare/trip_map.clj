(ns bikeshare.trip-map
  (:require
   [quil.core :as q]
   [quil.middleware :as m]
   [bikeshare.stations :as station]
   [bikeshare.trips :as trip]))

(defonce trip-data (atom []))
(defonce trip-header (atom []))
(defonce station-data (atom []))
(defonce station-header (atom []))

(def WIDTH 1200)
(def HEIGHT 800)

(def MAP-MARGIN 10)
(def MAP-W (- WIDTH (* 2.0 MAP-MARGIN)))
(def MAP-H (- HEIGHT (* 2.0 MAP-MARGIN)))

(def DRAW-RATE 1) ;; frames per cycle
(def BAR-W 1)
(def MAX-TRIPS 144015)

(defn setup []
  (q/background 0)
  (q/stroke-weight 0.5)
  (trip/load-trip-data trip-header trip-data)
  (station/load-station-data station-header station-data)
  
  (let [sf-station-ids (station/sf-stations @station-data)
        stations (station/station-lookup @station-data)
        ;;; SF-STATIONS
        stations (into {}
                       (filter (fn [[station-id _]]
                                 (sf-station-ids station-id))
                               stations))
        trips (map (fn [trip]
                     {:start-station-id (trip/start-station-id trip)
                      :end-station-id (trip/end-station-id trip)})
                   (take-last MAX-TRIPS @trip-data))
        ;;; SF-TRIPS
        trips (filter (fn [{:keys [start-station-id end-station-id]}]
                        (and (sf-station-ids start-station-id)
                             (sf-station-ids end-station-id)))
                      trips)
        coords (vals stations)
        lats (map first coords)
        longs (map last coords)]
    {:trips trips
     :stations stations
     :lat-bounds [(apply min lats) (apply max lats)]
     :long-bounds [(apply min longs) (apply max longs)]
     :cycle 0}))

(defn update [state]
  (assoc-in state [:cycle] (mod (int (/ (q/frame-count) DRAW-RATE))
                                MAX-TRIPS)))

(defn coord->xy
  "Must invert y, due to difference between lat and P5 y axis."
  [[lat-min lat-max] [long-min long-max] [lat long]]
  [(q/map-range long long-min long-max 0     MAP-W)
   (q/map-range lat  lat-min  lat-max  MAP-H 0)])

(defn draw-trip
  [lat-bounds long-bounds stations {:keys [start-station-id end-station-id]}]
  (let [start-coord (get stations start-station-id)
        end-coord   (get stations end-station-id)
        [start-x start-y] (coord->xy lat-bounds long-bounds start-coord)
        [end-x   end-y  ] (coord->xy lat-bounds long-bounds end-coord)]
    (q/line start-x start-y end-x end-y)))

(def BACKGROUND-ALPHA 1.0)

(defn draw
  [{:keys [trips stations lat-bounds long-bounds cycle]}]
  (q/stroke 0 BACKGROUND-ALPHA)
  (q/fill 0 BACKGROUND-ALPHA)
  (q/rect 0 0 WIDTH HEIGHT)
  
  (q/stroke 255)
  (q/fill 255)
  (let [trip (nth trips cycle)]
    (q/with-translation [MAP-MARGIN MAP-MARGIN]
      (draw-trip lat-bounds long-bounds stations trip))))

(q/defsketch bikeshare
  :title "Trips"
  :size [WIDTH HEIGHT]
  :setup setup
  :update update
  :draw draw
  :middleware [m/fun-mode])
