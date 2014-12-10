(ns bikeshare.trips
  (:require 
   [bikeshare.load :as load]))

(def IDX-DURATION       1)
(def IDX-START-TERMINAL 4)
(def IDX-END-TERMINAL   7)

;;; There are 144,015 trips.
;;; The first row is the heading:
;;; ["Trip ID" "Duration" "Start Date" "Start Station" "Start Terminal" "End Date" "End Station" "End Terminal" "Bike #" "Subscription Type" "Zip Code"]
(defn load-trip-data [header-atom data-atom]
  (let [data (load/lazy-read-csv "../../Data/201402_trip_data.csv")]
    (reset! header-atom (first data))
    (reset! data-atom (drop 1 data)))
  nil)

(defn duration [trip]
  (Integer/parseInt (nth trip IDX-DURATION)))

(defn durations [trips n]
  (map duration (take-last n trips)))

(defn start-station-id [trip]
  (Integer/parseInt (nth trip IDX-START-TERMINAL)))

(defn end-station-id [trip]
  (Integer/parseInt (nth trip IDX-END-TERMINAL)))
