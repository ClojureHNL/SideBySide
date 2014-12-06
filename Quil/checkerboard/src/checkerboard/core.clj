(ns checkerboard.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def WIDTH 500)
(def HEIGHT 500)

(def ROWS 10)
(def COLS 10)

(defn setup []
  {})

(defn update [state]
  state)

(defn draw [state]
  ; Clear the sketch by filling it with black color.
  (q/background 0)
  ; Set color.
  (q/fill 255)
  (let [col-width (/ WIDTH (float COLS))
        row-height (/ HEIGHT (float ROWS))]
    (doseq [row (range 0 ROWS)
            col (range 0 COLS)
            :when (or (and (even? row) (even? col))
                      (and (odd? row) (odd? col)))
            :let [x (* col-width col)
                  y (* row-height row)]]
      (q/rect x y col-width row-height)))
  )

(q/defsketch checkerboard
  :title "Checkerboard"
  :size [WIDTH HEIGHT]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update is called on each iteration before draw is called.
  ; It updates sketch state.
  :update update
  :draw draw
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
