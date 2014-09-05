package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Kessig Cagebreakers")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ROGUE})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class KessigCagebreakers extends Card
{
	public static final class KessigCagebreakersAbility0 extends EventTriggeredAbility
	{
		public KessigCagebreakersAbility0(GameState state)
		{
			super(state, "Whenever Kessig Cagebreakers attacks, put a 2/2 green Wolf creature token onto the battlefield tapped and attacking for each creature card in your graveyard.");
			this.addPattern(whenThisAttacks());

			SetGenerator number = Count.instance(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))));

			CreateTokensFactory factory = new CreateTokensFactory(number, numberGenerator(2), numberGenerator(2), "Put a 2/2 green Wolf creature token onto the battlefield tapped and attacking for each creature card in your graveyard.");
			factory.setColors(Color.GREEN);
			factory.setSubTypes(SubType.WOLF);
			factory.setTappedAndAttacking(null);
			this.addEffect(factory.getEventFactory());
		}
	}

	public KessigCagebreakers(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Whenever Kessig Cagebreakers attacks, put a 2/2 green Wolf creature
		// token onto the battlefield tapped and attacking for each creature
		// card in your graveyard.
		this.addAbility(new KessigCagebreakersAbility0(state));
	}
}
