package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Propaganda")
@Types({Type.ENCHANTMENT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Tempest.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Propaganda extends Card
{
	public static final class PropagandaAbility0 extends StaticAbility
	{
		public PropagandaAbility0(GameState state)
		{
			super(state, "Creatures can't attack you unless their controller pays (2) for each creature he or she controls that's attacking you.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_COST);
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("2")));
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(CreaturePermanents.instance()));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffectPart(part);
		}
	}

	public Propaganda(GameState state)
	{
		super(state);

		// Creatures can't attack you unless their controller pays (2) for each
		// creature he or she controls that's attacking you.
		this.addAbility(new PropagandaAbility0(state));
	}
}
