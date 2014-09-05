package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Joraga Warcaller")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.ELF})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class JoragaWarcaller extends Card
{
	public static final class OtherElvesBigger extends StaticAbility
	{
		public OtherElvesBigger(GameState state)
		{
			super(state, "Other Elf creatures you control get +1/+1 for each +1/+1 counter on Joraga Warcaller.");

			SetGenerator yourElves = Intersect.instance(HasSubType.instance(SubType.ELF), CREATURES_YOU_CONTROL);
			SetGenerator yourOtherElves = RelativeComplement.instance(yourElves, ABILITY_SOURCE_OF_THIS);
			SetGenerator amount = Count.instance(CountersOn.instance(This.instance(), Counter.CounterType.PLUS_ONE_PLUS_ONE));
			this.addEffectPart(modifyPowerAndToughness(yourOtherElves, amount, amount));
		}
	}

	public JoragaWarcaller(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Multikicker (1)(G)
		org.rnd.jmagic.abilities.keywords.Kicker kicker = new org.rnd.jmagic.abilities.keywords.Kicker(state, true, "(1)(G)");
		this.addAbility(kicker);

		// Joraga Warcaller enters the battlefield with a +1/+1 counter on it
		// for each time it was kicked.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, "Joraga Warcaller", ThisSpellWasKicked.instance(kicker.costCollections[0]), "a +1/+1 counter on it for each time it was kicked", Counter.CounterType.PLUS_ONE_PLUS_ONE));

		// Other Elf creatures you control get +1/+1 for each +1/+1 counter on
		// Joraga Warcaller.
		this.addAbility(new OtherElvesBigger(state));
	}
}
