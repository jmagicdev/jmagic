package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Steel Golem")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.GOLEM})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.WEATHERLIGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class SteelGolem extends Card
{
	public static final class ProhibitCreatures extends StaticAbility
	{
		public ProhibitCreatures(GameState state)
		{
			super(state, "You can't cast creature spells.");

			SimpleEventPattern castCreature = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			castCreature.put(EventType.Parameter.PLAYER, ControllerOf.instance(This.instance()));
			castCreature.put(EventType.Parameter.OBJECT, HasType.instance(Type.CREATURE));

			ContinuousEffect.Part prohibition = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			prohibition.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castCreature));
			this.addEffectPart(prohibition);
		}
	}

	public SteelGolem(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		this.addAbility(new ProhibitCreatures(state));
	}
}
