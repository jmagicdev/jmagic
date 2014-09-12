package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Kaboomist")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.WARRIOR})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class GoblinKaboomist extends Card
{
	public static final class Explode extends ActivatedAbility
	{
		public Explode(GameState state)
		{
			super(state, "(R), Sacrifice this artifact: This artifact deals 2 damage to target attacking creature without flying.");
			this.setManaCost(new ManaPool("(R)"));
			this.addCost(sacrificeThis("this artifact"));

			SetGenerator nonflyingAttacker = RelativeComplement.instance(Attacking.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			SetGenerator target = targetedBy(this.addTarget(nonflyingAttacker, "target attacking creature without flying"));
			this.addEffect(permanentDealDamage(2, target, "This artifact deals 2 damage to target attacking creature without flying."));
		}
	}

	public static final class GoblinKaboomistAbility0 extends EventTriggeredAbility
	{
		public GoblinKaboomistAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, put a colorless artifact token named Land Mine onto the battlefield with \"(R), Sacrifice this artifact: This artifact deals 2 damage to target attacking creature without flying.\" Then flip a coin. If you lose the flip, Goblin Kaboomist deals 2 damage to itself.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			CreateTokensFactory landMine = new CreateTokensFactory(numberGenerator(1), "Put a colorless artifact token named Land Mine onto the battlefield with \"(R), Sacrifice this artifact: This artifact deals 2 damage to target attacking creature without flying.\"");
			landMine.setArtifact();
			landMine.setName("Land Mine");
			landMine.addAbility(Explode.class);
			this.addEffect(landMine.getEventFactory());

			EventFactory flipCoin = new EventFactory(EventType.FLIP_COIN, "Flip a coin.");
			flipCoin.parameters.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory killYourself = permanentDealDamage(2, ABILITY_SOURCE_OF_THIS, "Goblin Kaboomist deals 2 damage to itself.");

			EventFactory maybeDie = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Then flip a coin. If you lose the flip, Goblin Kaboomist deals 2 damage to itself.");
			maybeDie.parameters.put(EventType.Parameter.IF, Identity.instance(flipCoin));
			maybeDie.parameters.put(EventType.Parameter.ELSE, Identity.instance(killYourself));
			this.addEffect(maybeDie);
		}
	}

	public GoblinKaboomist(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// At the beginning of your upkeep, put a colorless artifact token named
		// Land Mine onto the battlefield with
		// "(R), Sacrifice this artifact: This artifact deals 2 damage to target attacking creature without flying."
		// Then flip a coin. If you lose the flip, Goblin Kaboomist deals 2
		// damage to itself.
		this.addAbility(new GoblinKaboomistAbility0(state));
	}
}
