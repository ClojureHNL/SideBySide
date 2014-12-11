(ns bikeshare.stations
  (:require 
   [bikeshare.load :as load]))

(def IDX-STATION-ID       0)
(def IDX-STATION-LAT      2)
(def IDX-STATION-LONG     3)
(def IDX-STATION-LANDMARK 5)

(def LANDMARK-SF "San Francisco")
(def STATION-ID-CIVIC-CENTER 72)

;;; There are 70 stations.
;;; The first row is the heading:
;;; station_id,name,lat,long,dockcount,landmark,installation
;;; 2,San Jose Diridon Caltrain Station,37.329732,-121.901782,27,San Jose,8/6/2013
(defn load-station-data [header-atom data-atom]
  (let [data (load/lazy-read-csv "../../Data/201402_station_data.csv")]
    (reset! header-atom (first data))
    (reset! data-atom (drop 1 data)))
  nil)

(defn station-id [station]
  (Integer/parseInt (nth station IDX-STATION-ID)))

(defn station-lat [station]
  (Float/parseFloat (nth station IDX-STATION-LAT)))

(defn station-long [station]
  (Float/parseFloat (nth station IDX-STATION-LONG)))

(defn station-coord [station]
  [(station-lat station) (station-long station)])

(defn insert-station [lookup station]
  (assoc lookup (station-id station) (station-coord station)))

(defn station-lookup [stations]
  (reduce insert-station {} stations))

(defn sf-stations [stations]
  (set
   (map station-id
        (filter (fn [station]
                  (= LANDMARK-SF (nth station IDX-STATION-LANDMARK)))
                stations))))
