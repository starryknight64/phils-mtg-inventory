package mtginventory

class Meta {

    String name
    String value

    static constraints = {
        name unique: true
        value nullable: true
    }
}
