/*-----------------------------------------------------------------------------------
 * Kingsoft Office Enterprise Cloud Services.
 * Copyright (c) Kingsoft Office,  All rights reserved.
 *-----------------------------------------------------------------------------------*/

package http

import (
	"ksogit.kingsoft.net/docmini/fx/xfx"
	common_tag1 "ksogit.kingsoft.net/docmini/mapis/tag1"
	"tmp/modules/impl/base"
	"tmp/modules/impl/core"
)

type Tag1HttpImpl struct {
	base.BaseModules
	*core.Tag1CoreImpl
	// TODO
}

func NewImpl(app xfx.App, name string, coreImpl *core.Tag1CoreImpl, depends ...string) *Tag1HttpImpl {
	httpImpl := &Tag1HttpImpl{
		Tag1CoreImpl: coreImpl,
	}
	httpImpl.BaseModules = base.InitBaseModules(app, depends...)

	// todo
	return httpImpl
}

var _ common_tag1.HttpAPI = &Tag1HttpImpl{}
