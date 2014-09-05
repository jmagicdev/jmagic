package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.abilities.keywords.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Helm of Kaldra")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = FifthDawn.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class HelmofKaldra extends Card
{
	public static final class HelmofKaldraAbility0 extends StaticAbility
	{
		public HelmofKaldraAbility0(GameState state)
		{
			super(state, "Equipped creature has first strike, trample, and haste.");
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), FirstStrike.class, Trample.class, Haste.class));
		}
	}

	public static final class HelmofKaldraAbility1 extends ActivatedAbility
	{
		public HelmofKaldraAbility1(GameState state)
		{
			super(state, "(1): If you control Equipment named Helm of Kaldra, Sword of Kaldra, and Shield of Kaldra, put a legendary 4/4 colorless Avatar creature token named Kaldra onto the battlefield and attach those Equipment to it.");
			this.setManaCost(new ManaPool("(1)"));

			SetGenerator equipment = HasSubType.instance(SubType.EQUIPMENT);
			SetGenerator controlledByYou = ControlledBy.instance(You.instance());
			SetGenerator equipmentYouControl = Intersect.instance(equipment, controlledByYou);

			SetGenerator helm = Intersect.instance(equipmentYouControl, HasName.instance("Helm of Kaldra"));
			SetGenerator sword = Intersect.instance(equipmentYouControl, HasName.instance("Sword of Kaldra"));
			SetGenerator shield = Intersect.instance(equipmentYouControl, HasName.instance("Shield of Kaldra"));
			SetGenerator haveAll = Both.instance(Both.instance(helm, sword), shield);

			CreateTokensFactory token = new CreateTokensFactory(1, 4, 4, "Put a legendary 4/4 colorless Avatar creature token named Kaldra onto the battlefield");
			token.setLegendary();
			token.setSubTypes(SubType.AVATAR);
			token.setName("Kaldra");
			EventFactory factory = token.getEventFactory();

			EventFactory attach = attach(Union.instance(helm, sword, shield), EffectResult.instance(factory), "and attach those Equipment to it.");

			EventFactory effect = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If you control Equipment named Helm of Kaldra, Sword of Kaldra, and Shield of Kaldra, put a legendary 4/4 colorless Avatar creature token named Kaldra onto the battlefield and attach those Equipment to it.");
			effect.parameters.put(EventType.Parameter.IF, haveAll);
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(sequence(factory, attach)));
			this.addEffect(effect);
		}
	}

	public HelmofKaldra(GameState state)
	{
		super(state);

		// Equipped creature has first strike, trample, and haste.
		this.addAbility(new HelmofKaldraAbility0(state));

		// (1): If you control Equipment named Helm of Kaldra, Sword of Kaldra,
		// and Shield of Kaldra, put a legendary 4/4 colorless Avatar creature
		// token named Kaldra onto the battlefield and attach those Equipment to
		// it.
		this.addAbility(new HelmofKaldraAbility1(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
