package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Triplicate Spirits")
@Types({Type.SORCERY})
@ManaCost("4WW")
@ColorIdentity({Color.WHITE})
public final class TriplicateSpirits extends Card
{
	public TriplicateSpirits(GameState state)
	{
		super(state);

		// Convoke (Your creatures can help cast this spell. Each creature you
		// tap while casting this spell pays for (1) or one mana of that
		// creature's color.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Convoke(state));

		// Put three 1/1 white Spirit creature tokens with flying onto the
		// battlefield. (They can't be blocked except by creatures with flying
		// or reach.)
		CreateTokensFactory spirits = new CreateTokensFactory(3, 1, 1, "Put three 1/1 white Spirit creature tokens with flying onto the battlefield.");
		spirits.setColors(Color.WHITE);
		spirits.setSubTypes(SubType.SPIRIT);
		spirits.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
		this.addEffect(spirits.getEventFactory());
	}
}
