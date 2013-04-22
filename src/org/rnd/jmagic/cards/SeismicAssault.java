package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Seismic Assault")
@Types({Type.ENCHANTMENT})
@ManaCost("RRR")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EXODUS, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class SeismicAssault extends Card
{
	public static final class Pitch extends ActivatedAbility
	{
		public Pitch(GameState state)
		{
			super(state, "Discard a land card: Seismic Assault deals 2 damage to target creature or player.");

			SetGenerator landsInHand = Intersect.instance(InZone.instance(HandOf.instance(You.instance())), HasType.instance(Type.LAND));

			EventType.ParameterMap discardParameters = new EventType.ParameterMap();
			discardParameters.put(EventType.Parameter.CAUSE, This.instance());
			discardParameters.put(EventType.Parameter.PLAYER, You.instance());
			discardParameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			discardParameters.put(EventType.Parameter.CHOICE, landsInHand);
			this.addCost(new EventFactory(EventType.DISCARD_CHOICE, discardParameters, "Discard a land card"));

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(2, targetedBy(target), "Seismic Assault deals 2 damage to target creature or player."));
		}
	}

	public SeismicAssault(GameState state)
	{
		super(state);

		this.addAbility(new Pitch(state));
	}
}
