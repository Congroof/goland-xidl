/*-----------------------------------------------------------------------------------
 * Kingsoft Office Enterprise Cloud Services.
 * Copyright (c) Kingsoft Office,  All rights reserved.
 *-----------------------------------------------------------------------------------*/

package core

import (
	"ksogit.kingsoft.net/docmini/fx/xfx"
	common_tag1 "ksogit.kingsoft.net/docmini/mapis/tag1"
	"tmp/modules/impl/base"
)

type Tag1CoreImpl struct {
	base.BaseModules
	Tag1NoopImpl

	// TODO
}

func NewImpl(app xfx.App, name string, depends ...string) *Tag1CoreImpl {
	coreImpl := &Tag1CoreImpl{}
	coreImpl.BaseModules = base.InitBaseModules(app, depends...)

	// todo
	return coreImpl
}

var _ common_tag1.CoreAPI = &Tag1CoreImpl{}
