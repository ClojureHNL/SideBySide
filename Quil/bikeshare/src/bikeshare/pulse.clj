(ns bikeshare.pulse
  (:require
   [quil.core :as q]
   [quil.middleware :as m]
   [bikeshare.trips :as trip]))

(defonce trip-data (atom []))
(defonce trip-header (atom []))

(def WIDTH 1000)
(def HEIGHT 500)

(def DRAW-RATE 1) ;; frames per cycle
(def BAR-W 1)
(def MAX-TRIPS 1000)

(defn setup []
  (trip/load-trip-data trip-header trip-data)
  (q/rect-mode :center)
  (let [all-durations (trip/durations @trip-data MAX-TRIPS)
        max-duration (apply max all-durations)]
    {:durations all-durations
     :max-duration max-duration
     :cycle 0}))

(defn update [state]
  (assoc-in state [:cycle] (mod (int (/ (q/frame-count) DRAW-RATE))
                                MAX-TRIPS)))

(defn draw-bar [max-duration trip-duration col]
  (let [bar-h (* HEIGHT (float (/ trip-duration max-duration)))]
   (q/rect col (/ HEIGHT 2.0) BAR-W bar-h)))

(def BACKGROUND-ALPHA 2.0)

(defn draw [{:keys [durations max-duration cycle]}]
  (q/stroke 0 BACKGROUND-ALPHA)
  (q/fill 0 BACKGROUND-ALPHA)
  (q/rect (/ WIDTH 2.0) (/ HEIGHT 2.0) WIDTH HEIGHT)
  (q/stroke 255)
  (q/fill 255)
  (let [trip-duration (nth durations cycle)]
   (draw-bar max-duration trip-duration cycle)))

(q/defsketch bikeshare
  :title "Trips"
  :size [WIDTH HEIGHT]
  :setup setup
  :update update
  :draw draw
  :middleware [m/fun-mode])
