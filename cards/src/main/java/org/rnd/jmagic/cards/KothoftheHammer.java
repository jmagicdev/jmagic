package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Koth of the Hammer")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.KOTH})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.RED})
public final class KothoftheHammer extends Card
{
	public static final class KothoftheHammerAbility0 extends LoyaltyAbility
	{
		public KothoftheHammerAbility0(GameState state)
		{
			super(state, +1, "Untap target Mountain. It becomes a 4/4 red Elemental creature until end of turn. It's still a land.");
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(Permanents.instance(), HasSubType.instance(SubType.MOUNTAIN)), "target Mountain"));
			this.addEffect(untap(target, "Untap target Mountain."));

			Animator animate = new Animator(target, 4, 4);
			animate.addColor(Color.RED);
			animate.addSubType(SubType.ELEMENTAL);
			this.addEffect(createFloatingEffect("It becomes a 4/4 red Elemental creature until end of turn. It's still a land.", animate.getParts()));
		}
	}

	public static final class KothoftheHammerAbility1 extends LoyaltyAbility
	{
		public KothoftheHammerAbility1(GameState state)
		{
			super(state, -2, "Add (R) to your mana pool for each Mountain you control.");

			EventFactory factory = new EventFactory(EventType.ADD_MANA, "Add (R) to your mana pool.");
			factory.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			factory.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("(R)")));
			factory.parameters.put(EventType.Parameter.NUMBER, Count.instance(Intersect.instance(HasSubType.instance(SubType.MOUNTAIN), ControlledBy.instance(You.instance()))));
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(factory);
		}
	}

	public static final class KothoftheHammerAbility2 extends LoyaltyAbility
	{
		public static final class MountainPing extends StaticAbility
		{
			public static final class Ping extends ActivatedAbility
			{
				public Ping(GameState state)
				{
					super(state, "(T): This land deals 1 damage to target creature or player.");
					this.costsTap = true;
					Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
					this.addEffect(permanentDealDamage(1, targetedBy(target), "This land deals 1 damage to target creature or player."));
				}
			}

			public MountainPing(GameState state)
			{
				super(state, "Mountains you control have \"(T): This land deals 1 damage to target creature or player.\"");

				this.addEffectPart(addAbilityToObject(Intersect.instance(HasSubType.instance(SubType.MOUNTAIN), ControlledBy.instance(You.instance())), Ping.class));
			}
		}

		public KothoftheHammerAbility2(GameState state)
		{
			super(state, -5, "You get an emblem with \"Mountains you control have '(T): This land deals 1 damage to target creature or player.'\"");

			EventFactory factory = new EventFactory(EventType.CREATE_EMBLEM, "You get an emblem with \"Mountains you control have '(T): This land deals 1 damage to target creature or player.'\"");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.ABILITY, Identity.instance(MountainPing.class));
			factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			this.addEffect(factory);
		}
	}

	public KothoftheHammer(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(3);

		// +1: Untap target Mountain. It becomes a 4/4 red Elemental creature
		// until end of turn. It's still a land.
		this.addAbility(new KothoftheHammerAbility0(state));

		// -2: Add (R) to your mana pool for each Mountain you control.
		this.addAbility(new KothoftheHammerAbility1(state));

		// -5: You get an emblem with
		// "Mountains you control have '(T): This land deals 1 damage to target creature or player.'"
		this.addAbility(new KothoftheHammerAbility2(state));
	}
}
