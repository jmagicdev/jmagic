package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Echo Mage")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class EchoMage extends Card
{
	public static final class EchoMageAbility3 extends ActivatedAbility
	{
		public EchoMageAbility3(GameState state)
		{
			super(state, "(U)(U), (T): Copy target instant or sorcery spell. You may choose new targets for the copy.");
			this.setManaCost(new ManaPool("(U)(U)"));
			this.costsTap = true;

			Target target = this.addTarget(Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), Spells.instance()), "target instant or sorcery spell");

			EventFactory factory = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "Copy target instant or sorcery spell. You may choose new targets for the copy.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			this.addEffect(factory);
		}
	}

	public static final class EchoMageAbility6 extends ActivatedAbility
	{
		public EchoMageAbility6(GameState state)
		{
			super(state, "(U)(U), (T): Copy target instant or sorcery spell twice. You may choose new targets for the copies.");
			this.setManaCost(new ManaPool("(U)(U)"));
			this.costsTap = true;

			Target target = this.addTarget(Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), Spells.instance()), "target instant or sorcery");

			EventFactory factory = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "Copy target instant or sorcery spell. You may choose new targets for the copy.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			this.addEffect(factory);
		}
	}

	public EchoMage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Level up (1)(U)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LevelUp(state, "(1)(U)"));

		// LEVEL 2-3
		// 2/4
		// (U)(U), (T): Copy target instant or sorcery spell. You may choose new
		// targets for the copy.
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 2, 3, 2, 4, "\"(U)(U), (T): Copy target instant or sorcery spell. You may choose new targets for the copy.\"", EchoMageAbility3.class));

		// LEVEL 4+
		// 2/5
		// (U)(U), (T): Copy target instant or sorcery spell twice. You may
		// choose new targets for the copies.
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 4, 2, 5, "\"(U)(U), (T): Copy target instant or sorcery spell twice. You may choose new targets for the copies.\"", EchoMageAbility6.class));
	}
}
