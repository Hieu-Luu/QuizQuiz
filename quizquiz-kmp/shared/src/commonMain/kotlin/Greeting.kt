class Greeting(private val platform: Platform) {
//    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}