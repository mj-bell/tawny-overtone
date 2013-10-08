(ns tawny-overtone.music-ontology-test
  (:use [clojure.test])
  (:require [tawny-overtone.music-ontology :as m]))


(deftest load-correct
  (is
   (instance? org.semanticweb.owlapi.model.OWLOntology
              m/music-ontology)))

(deftest test-orchestration
  (is
   (instance? org.semanticweb.owlapi.model.OWLClass
              m/Orchestration)))

(deftest test-condutor
  (is
   (instance? org.semanticweb.owlapi.model.OWLObjectProperty
              m/conductor)))

(deftest test-genre
  (is
   (instance? org.semanticweb.owlapi.model.OWLAnnotationProperty
              m/genre)))

(deftest test-channels
  (is
   (instance? org.semanticweb.owlapi.model.OWLDataProperty
              m/channels)))
