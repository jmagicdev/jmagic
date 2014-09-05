package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ghostly Prison")
@Types({Type.ENCHANTMENT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = ChampionsOfKamigawa.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class GhostlyPrison extends Card
{
	public static final class GhostlyPrisonAbility0 extends StaticAbility
	{
		public GhostlyPrisonAbility0(GameState state)
		{
			super(state, "Creatures can't attack you unless their controller pays (2) for each creature he or she controls that's attacking you.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_COST);
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("2")));
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, CreaturePermanents.instance());
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffectPart(part);
		}
	}

	public GhostlyPrison(GameState state)
	{
		super(state);

		// Creatures can't attack you unless their controller pays (2) for each
		// creature he or she controls that's attacking you.
		this.addAbility(new GhostlyPrisonAbility0(state));
	}
}
