/*
 * The MIT License
 *
 * Copyright (c) 2014-2015, Matthew DeTullio
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.mjdetullio.jenkins.plugins.multibranch;

import hudson.Extension;
import hudson.matrix.MatrixBuild;
import hudson.matrix.MatrixProject;
import hudson.model.Item;
import jenkins.branch.Branch;
import jenkins.branch.BranchProjectFactoryDescriptor;
import jenkins.branch.MultiBranchProject;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * @author Matthew DeTullio
 */
public final class MatrixBranchProjectFactory
        extends TemplateDrivenBranchProjectFactory<MatrixProject, MatrixBuild> {

    /**
     * No-op constructor used for data binding.
     */
    @DataBoundConstructor
    public MatrixBranchProjectFactory() {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatrixProject newInstance(Branch branch) {
        MatrixProject project = new MatrixProject(getOwner(), branch.getEncodedName());
        setBranch(project, branch);
        return project;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isProject(Item item) {
        return item instanceof MatrixProject &&
                ((MatrixProject) item).getProperty(BranchProjectProperty.class) != null;
    }

    /**
     * {@link MatrixBranchProjectFactory}'s descriptor.
     */
    @SuppressWarnings("unused")
    @Extension(optional = true)
    public static class DescriptorImpl extends BranchProjectFactoryDescriptor {
        @Override
        public String getDisplayName() {
            return "Fixed configuration";
        }

        @Override
        public boolean isApplicable(Class<? extends MultiBranchProject> clazz) {
            return MatrixMultiBranchProject.class.isAssignableFrom(clazz);
        }
    }
}