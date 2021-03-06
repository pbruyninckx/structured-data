(ns structured-data)

(defn do-a-thing [x]
  (let [x2 (+ x x)]
    (Math/pow x2 x2)))

(defn spiff [v]
  (+ (get v 0) (get v 2)))

(defn cutify [v]
  (conj v "<3"))

(defn spiff-destructuring [v]
  (let [[a b c] v]
    (+ a c)))

(defn point [x y]
  [x y])

(defn rectangle [bottom-left top-right]
  [bottom-left top-right])

(defn width [rectangle]
  (let
    [[[left bottom] [right top]] rectangle]
    (- right left)))

(defn height [rectangle]
  (let
    [[[left bottom] [right top]] rectangle]
    (- top bottom)))

(defn square? [rectangle]
  (== (height rectangle) (width rectangle)))

(defn area [rectangle]
  (* (height rectangle) (width rectangle)))

(defn contains-point? [rectangle point]
  (let
    [[[left bottom] [right top]] rectangle
     [x y] point]
    (and
      (<= left x right)
      (<= bottom y top))))

(defn contains-rectangle? [outer inner]
  (let
    [[bl tr] inner]
    (and
      (contains-point? outer bl)
      (contains-point? outer tr))))

(defn title-length [book]
  (count (:title book)))

(defn author-count [book]
  (count (:authors book)))

(defn multiple-authors? [book]
  (> (author-count book) 1))

(defn add-author [book new-author]
  (let [authors (:authors book)
         new-authors (conj authors new-author)]
        (assoc book :authors new-authors)))

(defn alive? [author]
  (not (:death-year author)))

(defn element-lengths [collection]
  (map count collection))

(defn second-elements [collection]
  (map second collection))

(defn titles [books]
  (map :title books))

(defn monotonic? [a-seq]
  (boolean (some identity (map (fn [op] (apply op a-seq)) [ >= <=]))))

(defn stars [n]
  (apply str (repeat n "*")))

(defn toggle [a-set elem]
  ((if (contains? a-set elem)
     disj
     conj) a-set elem))

(defn contains-duplicates? [a-seq]
  (not (= (count a-seq) (count (set a-seq)))))

(defn old-book->new-book [book]
  (assoc book
    :authors
    (set (:authors book))))

(defn has-author? [book author]
  (contains? (set (:authors book)) author))

(defn authors [books]
  (apply clojure.set/union (map :authors books)))

(defn all-author-names [books]
  (set (map :name (authors books))))

(defn author->string [author]
  (let [{name :name
         bdate :birth-year
         ddate :death-year} author]
    (str name
         (if bdate
           (str " (" bdate " - " ddate ")")))))

(defn authors->string [authors]
  (apply str (interpose ", " (map author->string authors))))

(defn book->string [book]
  (str (:title book)
       ", written by "
       (authors->string (:authors book))))


(defn books->string [books]
  (if (empty? books)
    "No books."
    (str (count books) " book" (if (< 1 (count books)) "s") ". "
         (apply str (interpose ". " (map book->string books)))
         ".")))

(defn books-by-author [author books]
  (filter (fn [book] (has-author? book author)) books))

(defn author-by-name [name authors]
  (first (filter (fn [author] (= (:name author) name)) authors)))

(defn living-authors [authors]
  (filter alive? authors))

(defn has-a-living-author? [book]
  (not (empty? (living-authors (:authors book)))))

(defn books-by-living-authors [books]
  (filter has-a-living-author? books))

; %________%
