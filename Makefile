.PHONY: deploy
deploy:
	clojure -X:test
	clojure -T:build jar
	clojure -T:build deploy
