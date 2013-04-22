package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Elvish Promenade")
@Types({Type.TRIBAL, Type.SORCERY})
@SubTypes({SubType.ELF})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class ElvishPromenade extends Card
{
	public ElvishPromenade(GameState state)
	{
		super(state);

		SetGenerator youControl = ControlledBy.instance(You.instance());
		SetGenerator elvesYouControl = Intersect.instance(HasSubType.instance(SubType.ELF), youControl);
		SetGenerator forEachElfYouControl = Count.instance(elvesYouControl);
		String effectName = "Put a 1/1 green Elf Warrior creature token onto the battlefield for each Elf you control.";
		CreateTokensFactory tokens = new CreateTokensFactory(forEachElfYouControl, numberGenerator(1), numberGenerator(1), effectName);
		tokens.setColors(Color.GREEN);
		tokens.setSubTypes(SubType.ELF, SubType.WARRIOR);
		this.addEffect(tokens.getEventFactory());
	}
}
