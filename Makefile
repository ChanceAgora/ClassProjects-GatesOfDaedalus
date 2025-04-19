JAVA = javac
SRC = $(wildcard source/*.java)
OUT = compiled

all: 
	@mkdir -p compiled && echo "Compiled folder created."
	$(JAVA) -d $(OUT) $(SRC)

clean:
	@rm -rf compiled && echo "Compiled folder succesfully removed."
