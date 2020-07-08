(ns arrival-test-task.prod
  (:require [arrival-test-task.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
