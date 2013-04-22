package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Urabrask the Hidden")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.PRAETOR})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.MYTHIC)})
@ColorIdentity({Color.RED})
public final class UrabrasktheHidden extends Card
{
	public static final class UrabrasktheHiddenAbility0 extends StaticAbility
	{
		public UrabrasktheHiddenAbility0(GameState state)
		{
			super(state, "Creatures you control have haste.");

			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Haste.class));
		}
	}

	public static final class UrabrasktheHiddenAbility1 extends StaticAbility
	{
		public UrabrasktheHiddenAbility1(GameState state)
		{
			super(state, "Creatures your opponents control enter the battlefield tapped.");

			SetGenerator stuff = HasType.instance(Type.CREATURE);
			SetGenerator opponents = OpponentsOf.instance(You.instance());

			ZoneChangeReplacementEffect gatekeeping = new ZoneChangeReplacementEffect(this.game, "Creatures your opponents control enter the battlefield tapped");
			gatekeeping.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), stuff, opponents, false));
			gatekeeping.addEffect(tap(NewObjectOf.instance(gatekeeping.replacedByThis()), "A creature enters the battlefield tapped."));
			this.addEffectPart(replacementEffectPart(gatekeeping));
		}
	}

	public UrabrasktheHidden(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Creatures you control have haste.
		this.addAbility(new UrabrasktheHiddenAbility0(state));

		// Creatures your opponents control enter the battlefield tapped.
		this.addAbility(new UrabrasktheHiddenAbility1(state));
	}
}
