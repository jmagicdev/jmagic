package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nomads' Assembly")
@Types({Type.SORCERY})
@ManaCost("4WW")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class NomadsAssembly extends Card
{
	public NomadsAssembly(GameState state)
	{
		super(state);

		String effectName = "Put a 1/1 white Kor Soldier creature token onto the battlefield for each creature you control.";
		CreateTokensFactory tokens = new CreateTokensFactory(Count.instance(CREATURES_YOU_CONTROL), numberGenerator(1), numberGenerator(1), effectName);
		tokens.setColors(Color.WHITE);
		tokens.setSubTypes(SubType.KOR, SubType.SOLDIER);
		this.addEffect(tokens.getEventFactory());

		// Rebound (If you cast this spell from your hand, exile it as it
		// resolves. At the beginning of your next upkeep, you may cast this
		// card from exile without paying its mana cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Rebound(state));
	}
}
