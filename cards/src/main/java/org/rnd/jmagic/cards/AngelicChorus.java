package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Angelic Chorus")
@Types({Type.ENCHANTMENT})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class AngelicChorus extends Card
{
	public static final class GainLife extends EventTriggeredAbility
	{
		public GainLife(GameState state)
		{
			super(state, "Whenever a creature enters the battlefield under your control, you gain life equal to its toughness.");

			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), CreaturePermanents.instance(), ControllerOf.instance(ABILITY_SOURCE_OF_THIS), false));

			SetGenerator thatCreature = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));
			SetGenerator lifeAmount = ToughnessOf.instance(thatCreature);
			this.addEffect(gainLife(You.instance(), lifeAmount, "You gain life equal to its toughness."));
		}
	}

	public AngelicChorus(GameState state)
	{
		super(state);

		this.addAbility(new GainLife(state));
	}
}
