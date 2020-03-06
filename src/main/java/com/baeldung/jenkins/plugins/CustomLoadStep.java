package com.baeldung.jenkins.plugins;

import hudson.Extension;

import org.jenkinsci.plugins.workflow.steps.AbstractStepDescriptorImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractStepImpl;

import org.kohsuke.stapler.DataBoundConstructor;

public class CustomLoadStep extends AbstractStepImpl {

    private final String path = "cicd/component.groovy";

  //  @DataBoundConstructor
    public CustomLoadStep() {
    }

    public String getPath() {
        return path;
    }

 //   @Extension
    public static class DescriptorImpl extends AbstractStepDescriptorImpl {
        public DescriptorImpl() {
            super(CustomLoadStepExecution.class);
        }

        @Override
        public String getFunctionName() {
            return "componentGroovyLoad";
        }

        @Override
        public String getDisplayName() {
            return "Custom - Evaluate a Groovy source file into the Pipeline script";
        }
    }

}
