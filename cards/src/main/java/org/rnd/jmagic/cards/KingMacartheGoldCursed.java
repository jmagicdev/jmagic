package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("King Macar, the Gold-Cursed")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN})
@ManaCost("2BB")
@ColorIdentity({Color.BLACK})
public final class KingMacartheGoldCursed extends Card
{
	public static final class Glitter extends ActivatedAbility
	{
		public Glitter(GameState state)
		{
			super(state, "Sacrifice this artifact: Add one mana of any color to your mana pool.");
			this.addCost(sacrificeThis("this artifact"));
			this.addEffect(addManaToYourManaPoolFromAbility("(WUBRG)"));
		}
	}

	public static final class KingMacartheGoldCursedAbility0 extends EventTriggeredAbility
	{
		public KingMacartheGoldCursedAbility0(GameState state)
		{
			super(state, "Inspired \u2014 Whenever King Macar, the Gold-Cursed becomes untapped, you may exile target creature. If you do, put a colorless artifact token named Gold onto the battlefield. It has \"Sacrifice this artifact: Add one mana of any color to your mana pool.\"");
			this.addPattern(inspired());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			EventFactory exile = exile(target, "Exile target creature");

			CreateTokensFactory gold = new CreateTokensFactory(numberGenerator(1), "Put a colorless artifact token named Gold onto the battlefield. It has \"Sacrifice this artifact: Add one mana of any color to your mana pool.\"");
			gold.setArtifact();
			gold.setName("Gold");
			gold.addAbility(Glitter.class);

			this.addEffect(ifThen(youMay(exile), gold.getEventFactory(), "You may exile target creature. If you do, put a colorless artifact token named Gold onto the battlefield. It has \"Sacrifice this artifact: Add one mana of any color to your mana pool.\""));
		}
	}

	public KingMacartheGoldCursed(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Inspired \u2014 Whenever King Macar, the Gold-Cursed becomes
		// untapped, you may exile target creature. If you do, put a colorless
		// artifact token named Gold onto the battlefield. It has
		// "Sacrifice this artifact: Add one mana of any color to your mana pool."
		this.addAbility(new KingMacartheGoldCursedAbility0(state));
	}
}
