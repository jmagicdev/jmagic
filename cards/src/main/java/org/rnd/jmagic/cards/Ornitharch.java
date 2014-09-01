package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Ornitharch")
@Types({Type.CREATURE})
@SubTypes({SubType.ARCHON})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.BORN_OF_THE_GODS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class Ornitharch extends Card
{
	public static final class OrnitharchAbility2 extends EventTriggeredAbility
	{
		public OrnitharchAbility2(GameState state)
		{
			super(state, "When Ornitharch enters the battlefield, if tribute wasn't paid, put two 1/1 white Bird creature tokens with flying onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = org.rnd.jmagic.abilities.keywords.Tribute.WasntPaid.instance();

			CreateTokensFactory birds = new CreateTokensFactory(2, 1, 1, "Put two 1/1 white Bird creature tokens with flying onto the battlefield.");
			birds.setColors(Color.WHITE);
			birds.setSubTypes(SubType.BIRD);
			birds.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(birds.getEventFactory());
		}
	}

	public Ornitharch(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Tribute 2 (As this creature enters the battlefield, an opponent of your choice may place two +1/+1 counters on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Tribute(state, 2));

		// When Ornitharch enters the battlefield, if tribute wasn't paid, put two 1/1 white Bird creature tokens with flying onto the battlefield.
		this.addAbility(new OrnitharchAbility2(state));
	}
}
