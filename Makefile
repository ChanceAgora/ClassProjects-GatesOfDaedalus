JAVA = javac
SRC = $(wildcard source/*.java)
OUT = compiled

all: 
	@mkdir -p compiled
	$(JAVA) -d $(OUT) $(SRC)

clean:
	rm -rf compiled
