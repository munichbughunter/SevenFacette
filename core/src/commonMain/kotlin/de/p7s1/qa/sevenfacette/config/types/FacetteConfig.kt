package de.p7s1.qa.sevenfacette.config.types

/**
 * Singelton that holds the configuration data globally for the user.
 * In the actual class
 * * Create an init which loads the configuration of the ConfigReader,
 * * then map the results to the objects properties.
 *
 * @property httpClients List of httpClientConfiguration
 * @property custom map to give the users the possibility to add custom configurations
 *
 * @author Florian Pilz
 */
expect object FacetteConfig {
    var http: DHttpConfig?
        private set
    var custom: Map<String, String>?
        private set
    var kafka: DKafkaConfig?
        private set
    var database: Map<String, DDatabaseConfig>?
        private set
    var application: DApplicationConfig?
        private set
    var web: DWebConfig?
        private set
    var log: LoggingConfig?
        private set

    fun update()

    fun set(config: DFacetteConfig)

    fun reset()
}
