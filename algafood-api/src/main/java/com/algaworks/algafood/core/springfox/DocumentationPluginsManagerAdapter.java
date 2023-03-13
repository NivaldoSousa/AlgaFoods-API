package com.algaworks.algafood.core.springfox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.DefaultsProviderPlugin;
import springfox.documentation.spi.service.ResourceGroupingStrategy;
import springfox.documentation.spi.service.contexts.DocumentationContextBuilder;
import springfox.documentation.spring.web.SpringGroupingStrategy;
import springfox.documentation.spring.web.plugins.DefaultConfiguration;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;

@SuppressWarnings("deprecation")
@Primary // informa que esse componente será usado ao invés do componente DocumentationPluginsManager no contexto do spring
@Component
public class DocumentationPluginsManagerAdapter extends DocumentationPluginsManager {

    @Autowired
    @Qualifier("defaultsProviderPluginRegistry")
    private PluginRegistry<DefaultsProviderPlugin, DocumentationType> defaultsProviders;

    @Autowired
    @Qualifier("resourceGroupingStrategyRegistry")
    private PluginRegistry<ResourceGroupingStrategy, DocumentationType> resourceGroupingStrategies;

    @Override
    public DocumentationContextBuilder createContextBuilder(DocumentationType documentationType,
                                                            DefaultConfiguration defaultConfiguration) {

        return ((DefaultsProviderPlugin) this.defaultsProviders.getPluginOrDefaultFor(documentationType, defaultConfiguration))
                .create(documentationType).withResourceGroupingStrategy(this.resourceGroupingStrategy(documentationType));
    }

    @Override
    public ResourceGroupingStrategy resourceGroupingStrategy(DocumentationType documentationType) {

        return (ResourceGroupingStrategy)this.resourceGroupingStrategies
                .getPluginOrDefaultFor(documentationType, new SpringGroupingStrategy());
    }
}
