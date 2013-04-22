package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Figure of Destiny")
@Types({Type.CREATURE})
@SubTypes({SubType.KITHKIN})
@ManaCost("(R/W)")
@Printings({@Printings.Printed(ex = Expansion.EVENTIDE, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class FigureofDestiny extends Card
{
	public static final class FigureofDestinyAbility0 extends ActivatedAbility
	{
		public FigureofDestinyAbility0(GameState state)
		{
			super(state, "(RW): Figure of Destiny becomes a 2/2 Kithkin Spirit.");
			this.setManaCost(new ManaPool("(RW)"));

			Animator animator = new Animator(ABILITY_SOURCE_OF_THIS, 2, 2);
			animator.addSubType(SubType.KITHKIN);
			animator.addSubType(SubType.SPIRIT);
			EventFactory big = createFloatingEffect("Figure of Destiny becomes a 2/2 Kithkin Spirit.", animator.getParts());
			big.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
			this.addEffect(big);
		}
	}

	public static final class FigureofDestinyAbility1 extends ActivatedAbility
	{
		public FigureofDestinyAbility1(GameState state)
		{
			super(state, "(RW)(RW)(RW): If Figure of Destiny is a Spirit, it becomes a 4/4 Kithkin Spirit Warrior.");
			this.setManaCost(new ManaPool("(RW)(RW)(RW)"));

			Animator animator = new Animator(ABILITY_SOURCE_OF_THIS, 4, 4);
			animator.addSubType(SubType.KITHKIN);
			animator.addSubType(SubType.SPIRIT);
			animator.addSubType(SubType.WARRIOR);
			EventFactory bigger = createFloatingEffect("Figure of Destiny becomes a 4/4 Kithkin Spirit Warrior", animator.getParts());
			bigger.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));

			EventFactory effect = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If Figure of Destiny is a Spirit, it becomes a 4/4 Kithkin Spirit Warrior.");
			effect.parameters.put(EventType.Parameter.IF, Intersect.instance(ABILITY_SOURCE_OF_THIS, HasSubType.instance(SubType.SPIRIT)));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(bigger));
			this.addEffect(effect);
		}
	}

	public static final class FigureofDestinyAbility2 extends ActivatedAbility
	{
		public FigureofDestinyAbility2(GameState state)
		{
			super(state, "(RW)(RW)(RW)(RW)(RW)(RW): If Figure of Destiny is a Warrior, it becomes an 8/8 Kithkin Spirit Warrior Avatar with flying and first strike.");
			this.setManaCost(new ManaPool("(RW)(RW)(RW)(RW)(RW)(RW)"));

			Animator animator = new Animator(ABILITY_SOURCE_OF_THIS, 8, 8);
			animator.addSubType(SubType.KITHKIN);
			animator.addSubType(SubType.SPIRIT);
			animator.addSubType(SubType.WARRIOR);
			animator.addSubType(SubType.AVATAR);
			animator.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			animator.addAbility(org.rnd.jmagic.abilities.keywords.FirstStrike.class);
			EventFactory biggest = createFloatingEffect("Figure of Destiny becomes a 8/8 Kithkin Spirit Warrior Avatar with flying and first strike", animator.getParts());
			biggest.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));

			EventFactory effect = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If Figure of Destiny is a Warrior, it becomes an 8/8 Kithkin Spirit Warrior Avatar with flying and first strike.");
			effect.parameters.put(EventType.Parameter.IF, Intersect.instance(ABILITY_SOURCE_OF_THIS, HasSubType.instance(SubType.WARRIOR)));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(biggest));
			this.addEffect(effect);
		}
	}

	public FigureofDestiny(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// ((r/w)): Figure of Destiny becomes a 2/2 Kithkin Spirit.
		this.addAbility(new FigureofDestinyAbility0(state));

		// ((r/w)((r/w)((r/w)): If Figure of Destiny is a Spirit, it becomes a
		// 4/4 Kithkin Spirit Warrior.
		this.addAbility(new FigureofDestinyAbility1(state));

		// ((r/w)((r/w)((r/w)((r/w)((r/w)((r/w)): If Figure of Destiny is a
		// Warrior, it becomes an 8/8 Kithkin Spirit Warrior Avatar with flying
		// and first strike.
		this.addAbility(new FigureofDestinyAbility2(state));
	}
}
