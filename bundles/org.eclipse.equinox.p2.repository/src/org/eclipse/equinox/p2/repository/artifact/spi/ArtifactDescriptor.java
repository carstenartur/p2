/*******************************************************************************
 * Copyright (c) 2007, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.equinox.p2.repository.artifact.spi;

import java.util.Arrays;
import java.util.Map;
import org.eclipse.equinox.internal.p2.core.helpers.OrderedProperties;
import org.eclipse.equinox.p2.metadata.IArtifactKey;
import org.eclipse.equinox.p2.repository.artifact.*;

/**
 * This represents information about a given artifact stored on a particular byte server.
 * @since 2.0
 */
public class ArtifactDescriptor implements IArtifactDescriptor {
	private static final IProcessingStepDescriptor[] EMPTY_STEPS = new ProcessingStepDescriptor[0];

	protected IArtifactKey key; // The key associated with this artifact

	// The list of post processing steps that must be applied one the artifact once it 
	// has been downloaded (e.g, unpack, then md5 checksum, then...)
	protected IProcessingStepDescriptor[] processingSteps = EMPTY_STEPS;

	protected Map<String, String> properties = new OrderedProperties();
	protected transient IArtifactRepository repository;

	public ArtifactDescriptor(IArtifactDescriptor base) {
		super();
		key = base.getArtifactKey();
		processingSteps = base.getProcessingSteps();
		properties.putAll(base.getProperties());
		repository = base.getRepository();
	}

	public ArtifactDescriptor(ArtifactDescriptor base) {
		super();
		key = base.key;
		processingSteps = base.processingSteps;
		properties.putAll(base.properties);
		repository = base.repository;
	}

	public ArtifactDescriptor(IArtifactKey key) {
		super();
		this.key = key;
	}

	public IArtifactKey getArtifactKey() {
		return key;
	}

	public String getProperty(String propertyKey) {
		return properties.get(propertyKey);
	}

	public void setProperty(String key, String value) {
		if (value == null)
			properties.remove(key);
		else
			properties.put(key, value);
	}

	public void addProperties(Map<String, String> additionalProperties) {
		properties.putAll(additionalProperties);
	}

	/**
	 * Returns a read-only collection of the properties of the artifact descriptor.
	 * @return the properties of this artifact descriptor.
	 */
	public Map<String, String> getProperties() {
		return OrderedProperties.unmodifiableProperties(properties);
	}

	public IProcessingStepDescriptor[] getProcessingSteps() {
		return processingSteps;
	}

	public void setProcessingSteps(IProcessingStepDescriptor[] value) {
		processingSteps = value == null ? EMPTY_STEPS : value;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;

		// Other implementations of IArtifactDescriptor must not be considered equal
		if (!(obj.getClass().equals(getClass())))
			return false;

		ArtifactDescriptor other = (ArtifactDescriptor) obj;
		if (key == null) {
			if (other.getArtifactKey() != null)
				return false;
		} else if (!key.equals(other.getArtifactKey()))
			return false;

		if (!Arrays.equals(processingSteps, other.getProcessingSteps()))
			return false;

		String format = getProperty(FORMAT);
		String otherFormat = other.getProperty(FORMAT);
		if (format != null ? !format.equals(otherFormat) : otherFormat != null)
			return false;

		return true;
	}

	public int hashCode() {
		String format = getProperty(FORMAT);

		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + Arrays.asList(processingSteps).hashCode();
		result = prime * result + (format != null ? format.hashCode() : 0);
		return result;
	}

	public IArtifactRepository getRepository() {
		return repository;
	}

	public void setRepository(IArtifactRepository value) {
		repository = value;
	}

	public String toString() {
		String format = getProperty(IArtifactDescriptor.FORMAT);
		if (format == null)
			return "canonical: " + key.toString(); //$NON-NLS-1$
		return format + ": " + key.toString(); //$NON-NLS-1$
	}

}