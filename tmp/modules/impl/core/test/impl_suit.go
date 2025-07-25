/*-----------------------------------------------------------------------------------
 * Kingsoft Office Enterprise Cloud Services.
 * Copyright (c) Kingsoft Office,  All rights reserved.
 *-----------------------------------------------------------------------------------*/

package test

import (
	"tmp/modules/impl/core"
)

type Tag1CoreImplSuite struct {
	impl *core.Tag1CoreImpl
	//todo
}

func (s *Tag1CoreImplSuite) SetupSuite() {
	s.impl = s.Intance()
}

func (s *Tag1CoreImplSuite) Intance() *core.Tag1CoreImpl {
	coreImpl := &core.Tag1CoreImpl{
		//todo
	}
	coreImpl.BaseModules = initBaseModules()

	// todo
	return coreImpl
}
