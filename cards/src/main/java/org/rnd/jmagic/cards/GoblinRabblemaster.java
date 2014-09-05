package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.expansions.*;

@Name("Goblin Rabblemaster")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.GOBLIN})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Magic2015CoreSet.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class GoblinRabblemaster extends Card
{
	public static final class GoblinRabblemasterAbility0 extends StaticAbility
	{
		public GoblinRabblemasterAbility0(GameState state)
		{
			super(state, "Other Goblin creatures you control attack each turn if able.");

			SetGenerator otherGoblinCreatures = RelativeComplement.instance(Intersect.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.GOBLIN)), This.instance());
			SetGenerator yourOtherGoblins = Intersect.instance(otherGoblinCreatures, ControlledBy.instance(You.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, yourOtherGoblins);
			this.addEffectPart(part);
		}
	}

	public static final class GoblinRabblemasterAbility1 extends EventTriggeredAbility
	{
		public GoblinRabblemasterAbility1(GameState state)
		{
			super(state, "At the beginning of combat on your turn, put a 1/1 red Goblin creature token with haste onto the battlefield.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, BeginningOfCombatStepOf.instance(You.instance()));
			this.addPattern(pattern);

			CreateTokensFactory goblin = new CreateTokensFactory(1, 1, 1, "Put a 1/1 red Goblin creature token with haste onto the battlefield.");
			goblin.setColors(Color.RED);
			goblin.addAbility(org.rnd.jmagic.abilities.keywords.Haste.class);
			this.addEffect(goblin.getEventFactory());
		}
	}

	public static final class GoblinRabblemasterAbility2 extends EventTriggeredAbility
	{
		public GoblinRabblemasterAbility2(GameState state)
		{
			super(state, "Whenever Goblin Rabblemaster attacks, it gets +1/+0 until end of turn for each other attacking Goblin.");
			this.addPattern(whenThisAttacks());

			SetGenerator otherGoblinCreatures = RelativeComplement.instance(Intersect.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.GOBLIN)), This.instance());
			SetGenerator otherAttackingGoblins = Intersect.instance(otherGoblinCreatures, Attacking.instance());
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, Count.instance(otherAttackingGoblins), numberGenerator(0), "Goblin Rabblemaster gets +1/+0 until end of turn for each other attacking Goblin."));
		}
	}

	public GoblinRabblemaster(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Other Goblin creatures you control attack each turn if able.
		this.addAbility(new GoblinRabblemasterAbility0(state));

		// At the beginning of combat on your turn, put a 1/1 red Goblin
		// creature token with haste onto the battlefield.
		this.addAbility(new GoblinRabblemasterAbility1(state));

		// Whenever Goblin Rabblemaster attacks, it gets +1/+0 until end of turn
		// for each other attacking Goblin.
		this.addAbility(new GoblinRabblemasterAbility2(state));
	}
}
