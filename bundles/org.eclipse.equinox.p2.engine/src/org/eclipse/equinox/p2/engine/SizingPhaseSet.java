/*******************************************************************************
 * Copyright (c) 2007, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.equinox.p2.engine;

import org.eclipse.equinox.internal.p2.engine.Phase;
import org.eclipse.equinox.internal.p2.engine.PhaseSet;
import org.eclipse.equinox.internal.p2.engine.phases.Sizing;

/**
 * @since 2.0
 */
public class SizingPhaseSet extends PhaseSet {

	private static Sizing sizing;

	public SizingPhaseSet() {
		super(new Phase[] {sizing = new Sizing(100)});
	}

	public long getDiskSize() {
		return sizing.getDiskSize();
	}

	public long getDownloadSize() {
		return sizing.getDownloadSize();
	}
}