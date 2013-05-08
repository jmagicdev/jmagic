/**
 * 
 */
package org.rnd.jmagic.abilities;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class YouControlEnchantedCreature extends StaticAbility
{
	public YouControlEnchantedCreature(GameState state)
	{
		super(state, "You control enchanted creature.");

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, EnchantedBy.instance(This.instance()));
		part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());

		this.addEffectPart(part);
	}
}