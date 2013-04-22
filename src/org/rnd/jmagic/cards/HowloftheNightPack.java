package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Howl of the Night Pack")
@Types({Type.SORCERY})
@ManaCost("6G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class HowloftheNightPack extends Card
{
	public HowloftheNightPack(GameState state)
	{
		super(state);

		// Put a 2/2 green Wolf creature token onto the battlefield for each
		// Forest you control.
		SetGenerator forestsYouControl = Intersect.instance(HasSubType.instance(SubType.FOREST), ControlledBy.instance(You.instance()));
		String effectName = "Put a 2/2 green Wolf creature token onto the battlefield for each Forest you control.";
		CreateTokensFactory tokens = new CreateTokensFactory(Count.instance(forestsYouControl), numberGenerator(2), numberGenerator(2), effectName);
		tokens.setColors(Color.GREEN);
		tokens.setSubTypes(SubType.WOLF);
		this.addEffect(tokens.getEventFactory());
	}
}
