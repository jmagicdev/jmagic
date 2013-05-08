package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Archdemon of Unx")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("5BB")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class ArchdemonofUnx extends Card
{
	public static final class Nom extends EventTriggeredAbility
	{
		public Nom(GameState state)
		{
			super(state, "At the beginning of your upkeep, sacrifice a non-Zombie creature, then put a 2/2 black Zombie creature token onto the battlefield.");

			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(sacrifice(You.instance(), 1, RelativeComplement.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.ZOMBIE)), "Sacrifice a non-Zombie creature"));

			CreateTokensFactory token = new CreateTokensFactory(1, 2, 2, "then put a 2/2 black Zombie creature token onto the battlefield.");
			token.setColors(Color.BLACK);
			token.setSubTypes(SubType.ZOMBIE);
			this.addEffect(token.getEventFactory());
		}
	}

	public ArchdemonofUnx(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		this.addAbility(new Nom(state));
	}
}
