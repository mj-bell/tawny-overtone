;; The contents of this file are subject to the LGPL License, Version 3.0.

;; Copyright (C) 2013, Newcastle University

;; This program is free software: you can redistribute it and/or modify
;; it under the terms of the GNU General Public License as published by
;; the Free Software Foundation, either version 3 of the License, or
;; (at your option) any later version.

;; This program is distributed in the hope that it will be useful,
;; but WITHOUT ANY WARRANTY; without even the implied warranty of
;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
;; GNU General Public License for more details.

;; You should have received a copy of the GNU General Public License
;; along with this program.  If not, see http://www.gnu.org/licenses/.

(ns tawny-overtone.music-ontology
  (:refer-clojure :only [fn println some = last let spit
                          doseq format str get instance?])
  (:require [tawny.read] [tawny.render])
  (:import
   (org.semanticweb.owlapi.util SimpleIRIMapper)
   (org.semanticweb.owlapi.model IRI)))

;;Can't do this through materialize-ontology, due to similarity
;;ontology hanging. Create map manually
(def resources-map {"http://purl.org/ontology/similarity/", "musim.owl"
                      "http://purl.org/ontology/ao/core#", "counterontology.owl"
                      "http://purl.org/vocab/bio/0.1/", "bio.rdf"
                      "http://web.resource.org/cc/", "copyright.rdf"
                      "http://purl.org/NET/c4dm/event.owl", "event.n3"
                      "http://xmlns.com/foaf/0.1/", "foaf.rdf"
                      "http://purl.org/vocab/frbr/core#", "core.rdf"
                      "http://xmlns.com/wot/0.1/", "wot.rdf"
                      "http://purl.org/NET/c4dm/keys.owl#", "keys.owl"
                      "http://www.w3.org/2006/time#", "time.rdf"
                      "http://www.w3.org/2003/06/sw-vocab-status/ns#", "ns.rdf"
                      "http://www.w3.org/1999/02/22-rdf-syntax-ns#", "rdfsyntax.rdf"
                      "http://www.w3.org/2002/07/owl#", "owl.n3"
                      "http://www.w3.org/2000/01/rdf-schema#", "rdf-schema.rdf"
                      "http://purl.org/ontology/mo/", "musicontology.rdfs"
                      "http://purl.org/dc/terms/", "dcterms.rdf"
                      "http://purl.org/NET/dc_owl2dl/dcam", "dcam.owl"
                      "http://purl.org/NET/dc_owl2dl/dcmitype", "dcmitype.owl"
                      "http://purl.org/NET/dc_owl2dl/elements", "dc.owl"
                      "http://purl.org/dc/elements/1.1/" "dc.owl"})



(tawny.read/defread music-ontology
  :location (tawny.owl/iri (clojure.java.io/resource "musicontology.rdfs"))
  :iri "http://purl.org/ontology/mo/"
  :prefix "mus:"
  :transform ;; Remove root of IRI
  (fn [e]
    ;;(println "Transforming: " e)
    (try
      (.getFragment
       (.getIRI e))
      (catch Exception e
        (println 
         (clojure.core/str "Oh gods!" (.getMessage e))))))
  :filter ; Remove those not part of music ontology
  (fn [e]
    ;;(println "Filtering:" (.getIRI e))
    (clojure.core/and
     (clojure.core/not
      (= "http://purl.org/ontology/mo/"
         (.toString (.getIRI e)))) ;;Check IRI isn't equal to root
     (tawny.read/iri-starts-with-filter
      "http://purl.org/ontology/mo/" e))) ;;Check IRI starts with root
                                          ;;and is a type of OWLNamedObject
  :mapper (tawny.read/resource-iri-mapper resources-map)
)

