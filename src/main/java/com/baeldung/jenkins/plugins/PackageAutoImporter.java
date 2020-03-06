package com.baeldung.jenkins.plugins;

import com.google.inject.Inject;
import groovy.lang.GroovyShell;
import hudson.Extension;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.jenkinsci.plugins.workflow.cps.CpsFlowExecution;
import org.jenkinsci.plugins.workflow.cps.GroovyShellDecorator;

import javax.annotation.CheckForNull;
import java.util.logging.Logger;


//@Extension(optional = true)
public class PackageAutoImporter extends GroovyShellDecorator {

 //   @Inject
    private Logger logger;

    @Override
    public void configureShell(@CheckForNull CpsFlowExecution context, GroovyShell shell) {

    }

}

