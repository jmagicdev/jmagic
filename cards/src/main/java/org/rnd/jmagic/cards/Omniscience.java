package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Omniscience")
@Types({Type.ENCHANTMENT})
@ManaCost("7UUU")
@ColorIdentity({Color.BLUE})
public final class Omniscience extends Card
{
	public static final class OmniscienceAbility0 extends StaticAbility
	{
		public OmniscienceAbility0(GameState state)
		{
			super(state, "You may cast nonland cards from your hand without paying their mana costs.");

			SetGenerator you = You.instance();
			SetGenerator inYourHand = InZone.instance(HandOf.instance(you));
			SetGenerator lands = HasType.instance(Type.LAND);

			PlayPermission permission = new PlayPermission(you);
			permission.forceAlternateCost(Empty.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_LOCATION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, RelativeComplement.instance(inYourHand, lands));
			part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(permission));
			this.addEffectPart(part);
		}
	}

	public Omniscience(GameState state)
	{
		super(state);

		// You may cast nonland cards from your hand without paying their mana
		// costs.
		this.addAbility(new OmniscienceAbility0(state));
	}
}
