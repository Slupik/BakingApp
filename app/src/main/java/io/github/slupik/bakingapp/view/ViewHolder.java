/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

public abstract class ViewHolder {
	protected abstract Context getContext();

	protected static View getInflatedView(@LayoutRes int layoutID, Context context) {
		return LayoutInflater.from(context).inflate(layoutID, null);
	}
}
