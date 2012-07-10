/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.exoplatform.management.idm.operations;

import java.util.HashSet;
import java.util.Set;

import org.exoplatform.commons.utils.ListAccess;
import org.exoplatform.services.organization.OrganizationService;
import org.exoplatform.services.organization.User;
import org.gatein.management.api.exceptions.OperationException;
import org.gatein.management.api.operation.OperationContext;
import org.gatein.management.api.operation.OperationHandler;
import org.gatein.management.api.operation.OperationNames;
import org.gatein.management.api.operation.ResultHandler;
import org.gatein.management.api.operation.model.ReadResourceModel;

/**
 * @author <a href="mailto:thomas.delhomenie@exoplatform.com">Thomas Delhoménie</a>
 * @version $Revision$
 */
public class IdmReadResource implements OperationHandler
{
   @Override
   public void execute(OperationContext operationContext, ResultHandler resultHandler) throws OperationException
   {
	   try {
		   OrganizationService organizationService = operationContext.getRuntimeContext().getRuntimeComponent(OrganizationService.class);
		   ListAccess<User> allUsers = organizationService.getUserHandler().findAllUsers();
		   User[] allUsersArray = allUsers.load(0, allUsers.getSize());
		   
		   Set<String> users = new HashSet<String>();
		   
		   for(User user : allUsersArray) {
			   users.add(user.getUserName());
		   }
	
		   resultHandler.completed(new ReadResourceModel("Available users.", users));
	   } catch(Exception e) {
		   throw new OperationException(OperationNames.READ_RESOURCE, "Unable to retrieve the list of the users : " + e.getMessage());
	   }
   }
}
