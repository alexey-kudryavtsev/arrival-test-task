(ns arrival-test-task.core
  (:require
    [reagent.core :as reagent :refer [atom]]
    [reagent.dom :as rdom]))

(def long-lorem "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent a lorem tristique, viverra mauris a, iaculis nisl. Quisque id suscipit sem. Aliquam at felis malesuada, varius diam a, fringilla mi. Proin blandit porta mauris. Ut nec justo mollis ipsum placerat blandit eu ac massa. Nullam eget justo eu ipsum accumsan maximus. Praesent vel quam vitae velit eleifend mattis. Aenean dictum vitae enim nec convallis. Donec tortor quam, iaculis ac magna nec, varius luctus sem. Aliquam libero est, eleifend vel vestibulum vel, consequat et ante. Ut vitae mi et sapien tempor iaculis. Maecenas ac quam aliquet, tristique risus in, venenatis eros. Suspendisse iaculis id sem id suscipit. Duis vitae nulla vitae diam ullamcorper suscipit. Ut velit tellus, elementum sed tincidunt in, volutpat sed sapien. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.")

(def short-lorem "This is classical 'Lorem ipsum' placeholder.")

(defn current-page []
  (let [state (reagent/atom {:lorem :long})]
    (reagent/create-class
      {:component-did-mount
       (fn [c]
         (let [lore-text-block (last (.-children (rdom/dom-node c)))
               scroll-height (.-scrollHeight lore-text-block)
               client-height (.-clientHeight lore-text-block)]
           (if (> scroll-height client-height)
             (swap! state assoc :lorem :short)
             (swap! state assoc :lorem :long))))
       :display-name "current-page"
       :reagent-render
       (fn []
         [:div.page {:style {:display :flex
                             :flex-direction :row
                             :flex "1 1 auto"
                             :width "100%"
                             :height "50vh"
                             :border "1px solid black"
                             :padding "16px"}}
          [:div.resizable-block {:style {:flex "1 1 auto"
                                         :border "1px solid blue"
                                         :padding "16px"
                                         :margin "16px"
                                         :resize "both"
                                         :overflow "auto"
                                         :min-width "30%"}
                                 :contenteditable "true"}
           "Editable text"]
          [:div.lorem-text-block {:style {:flex "1 1 auto"
                                          :border "1px solid green"
                                          :padding "16px"
                                          :margin "16px"
                                          :resize "both"
                                          :overflow "auto"}}
           ;; TODO: add logic so that when there is enough space long-lorem text is displayed, otherwise display short-lorem text
           (if (= (:lorem @state) :short)
             short-lorem
             long-lorem)]])})))

;; -------------------------
;; Initialize app

(defn mount-root []
  (rdom/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
