(ns bikeshare.circles
  (:require
   [quil.core :as q]
   [quil.middleware :as m]
   [bikeshare.stations :as station]
   [bikeshare.trips :as trip]))

(defonce trip-data (atom []))
(defonce trip-header (atom []))

(def WIDTH 1200)
(def HEIGHT 800)

(def MAP-MARGIN 10)
(def MAP-W (- WIDTH (* 2.0 MAP-MARGIN)))
(def MAP-H (- HEIGHT (* 2.0 MAP-MARGIN)))

(def DRAW-RATE 1) ;; frames per cycle
(def BAR-W 1)
(def MAX-TRIPS 144015)

(defn by-start-station-id [station-id]
  (fn [trip]
    (= station-id (trip/start-station-id trip))))

(defn setup []
  (q/ellipse-mode :center)
  (q/background 0)
  (q/stroke-weight 0.5)
  (trip/load-trip-data trip-header trip-data)
  
  (let [filtered (filter (by-start-station-id station/STATION-ID-CIVIC-CENTER)
                         @trip-data)
        trips (take-last MAX-TRIPS filtered)
        all-durations (trip/durations trips MAX-TRIPS)
        max-duration (apply max all-durations)]
    {:trips trips
     :all-durations all-durations
     :max-duration max-duration
     :cycle 0}))

(defn update [{:keys [cycle all-durations] :as state}]
  (assoc-in state [:cycle] (mod (int (/ (q/frame-count) DRAW-RATE))
                                (count all-durations))))

(defn draw-trip
  [max-duration duration]
  (let [max-diameter (min MAP-W MAP-H)
        diameter (q/map-range duration 0 max-duration 0 max-diameter)]
    (q/ellipse 0 0 diameter diameter)))

(def BACKGROUND-ALPHA 1.0)

(defn draw
  [{:keys [all-durations max-duration cycle]}]
  (q/stroke 0 BACKGROUND-ALPHA)
  (q/fill 0 BACKGROUND-ALPHA)
  (q/rect 0 0 WIDTH HEIGHT)
  
  (q/stroke 75 188 252)
  (q/no-fill)
  (let [duration (nth all-durations cycle)]
    (q/with-translation [(/ WIDTH 2.0) (/ HEIGHT 2.0)]
      (draw-trip max-duration duration))))

(q/defsketch bikeshare
  :title "Trip Durations, Originating from Civic Center"
  :size [WIDTH HEIGHT]
  :setup setup
  :update update
  :draw draw
  :middleware [m/fun-mode])
