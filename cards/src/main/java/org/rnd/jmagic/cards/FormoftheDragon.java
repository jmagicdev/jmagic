package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Form of the Dragon")
@Types({Type.ENCHANTMENT})
@ManaCost("4RRR")
@Printings({@Printings.Printed(ex = FromTheVaultDragons.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Scourge.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class FormoftheDragon extends Card
{
	public static final class FormoftheDragonAbility0 extends EventTriggeredAbility
	{
		public FormoftheDragonAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, Form of the Dragon deals 5 damage to target creature or player.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(5, target, "Form of the Dragon deals 5 damage to target creature or player."));
		}
	}

	public static final class FormoftheDragonAbility1 extends EventTriggeredAbility
	{
		public FormoftheDragonAbility1(GameState state)
		{
			super(state, "At the beginning of each end step, your life total becomes 5.");
			this.addPattern(atTheBeginningOfEachEndStep());

			EventFactory setLife = new EventFactory(EventType.SET_LIFE, "Your life total becomes 5.");
			setLife.parameters.put(EventType.Parameter.CAUSE, This.instance());
			setLife.parameters.put(EventType.Parameter.NUMBER, numberGenerator(5));
			setLife.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(setLife);
		}
	}

	public static final class FormoftheDragonAbility2 extends StaticAbility
	{
		public FormoftheDragonAbility2(GameState state)
		{
			super(state, "Creatures without flying can't attack you.");

			SetGenerator hasFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
			SetGenerator creaturesWithoutFlyingAttackingYou = RelativeComplement.instance(Attacking.instance(You.instance()), hasFlying);
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(creaturesWithoutFlyingAttackingYou));
			this.addEffectPart(part);
		}
	}

	public FormoftheDragon(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, Form of the Dragon deals 5 damage to
		// target creature or player.
		this.addAbility(new FormoftheDragonAbility0(state));

		// At the beginning of each end step, your life total becomes 5.
		this.addAbility(new FormoftheDragonAbility1(state));

		// Creatures without flying can't attack you.
		this.addAbility(new FormoftheDragonAbility2(state));
	}
}
