package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Origin Spellbomb")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class OriginSpellbomb extends Card
{
	public static final class OriginSpellbombAbility0 extends ActivatedAbility
	{
		public OriginSpellbombAbility0(GameState state)
		{
			super(state, "(1), (T), Sacrifice Origin Spellbomb: Put a 1/1 colorless Myr artifact creature token onto the battlefield.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Origin Spellbomb"));

			CreateTokensFactory factory = new CreateTokensFactory(1, 1, 1, "Put a 1/1 colorless Myr artifact creature token onto the battlefield.");
			factory.setSubTypes(SubType.MYR);
			factory.setArtifact();
			this.addEffect(factory.getEventFactory());
		}
	}

	public OriginSpellbomb(GameState state)
	{
		super(state);

		// (1), (T), Sacrifice Origin Spellbomb: Put a 1/1 colorless Myr
		// artifact creature token onto the battlefield.
		this.addAbility(new OriginSpellbombAbility0(state));

		// When Origin Spellbomb is put into a graveyard from the battlefield,
		// you may pay (W). If you do, draw a card.
		this.addAbility(new org.rnd.jmagic.abilities.ScarsSpellbomb(state, this.getName(), "(W)"));
	}
}
