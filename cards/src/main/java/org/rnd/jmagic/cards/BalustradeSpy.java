package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Balustrade Spy")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.VAMPIRE})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class BalustradeSpy extends Card
{
	public static final class BalustradeSpyAbility1 extends EventTriggeredAbility
	{
		public BalustradeSpyAbility1(GameState state)
		{
			super(state, "When Balustrade Spy enters the battlefield, target player reveals cards from the top of his or her library until he or she reveals a land card, then puts those cards into his or her graveyard.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

			SetGenerator toReveal = TopMost.instance(LibraryOf.instance(target), numberGenerator(1), HasType.instance(Type.LAND));
			this.addEffect(reveal(toReveal, "Each opponent reveals cards from the top of his or her library until he or she reveals a land card,"));
			this.addEffect(putIntoGraveyard(toReveal, "then puts those cards into his or her graveyard."));
		}
	}

	public BalustradeSpy(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Balustrade Spy enters the battlefield, target player reveals
		// cards from the top of his or her library until he or she reveals a
		// land card, then puts those cards into his or her graveyard.
		this.addAbility(new BalustradeSpyAbility1(state));
	}
}
