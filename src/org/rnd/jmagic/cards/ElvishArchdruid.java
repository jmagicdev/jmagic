package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Elvish Archdruid")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.DRUID})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class ElvishArchdruid extends Card
{
	public static final class LotsOfMana extends ActivatedAbility
	{
		public LotsOfMana(GameState state)
		{
			super(state, "(T): Add (G) to your mana pool for each Elf you control.");

			// (T):
			this.costsTap = true;

			// Add (G) to your mana pool for each Elf you control.
			SetGenerator elvesYouControl = Intersect.instance(HasSubType.instance(SubType.ELF), ControlledBy.instance(You.instance()));
			SetGenerator numElvesYouControl = Count.instance(elvesYouControl);

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			parameters.put(EventType.Parameter.MANA, Identity.instance(new ManaPool("G")));
			parameters.put(EventType.Parameter.NUMBER, numElvesYouControl);
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(new EventFactory(EventType.ADD_MANA, parameters, "Add (G) to your mana pool for each Elf you control."));
		}
	}

	public ElvishArchdruid(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Other Elf creatures you control get +1/+1.
		SetGenerator elves = Intersect.instance(HasSubType.instance(SubType.ELF), CREATURES_YOU_CONTROL);
		SetGenerator who = RelativeComplement.instance(elves, This.instance());
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, who, "Other Elf creatures you control", +1, +1, true));

		this.addAbility(new LotsOfMana(state));
	}
}
