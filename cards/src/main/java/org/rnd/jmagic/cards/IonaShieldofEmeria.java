package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Iona, Shield of Emeria")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("6WWW")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class IonaShieldofEmeria extends Card
{
	public static final class ChooseAColor extends org.rnd.jmagic.abilityTemplates.AsThisEntersTheBattlefieldChooseAColor
	{
		public ChooseAColor(GameState state)
		{
			super(state, "Iona, Shield of Emeria");

			this.getLinkManager().addLinkClass(ProhibitCasts.class);
		}
	}

	// This pattern assumes that the color generator will evaluate to only one
	// color. If you move it outside of this card class, please update the match
	// method so that it accounts for more than one color.
	public static final class CastableOfColor implements SetPattern
	{
		private SetGenerator color;

		public CastableOfColor(SetGenerator color)
		{
			this.color = color;
		}

		@Override
		public boolean match(GameState state, Identified thisObject, Set set)
		{
			Set mostRecent = Identity.fromCollection(set).evaluate(state, thisObject);
			Color color = this.color.evaluate(state, thisObject).getOne(Color.class);
			for(Castable c: mostRecent.getAll(Castable.class))
				if(((GameObject)c).getColors().contains(color))
					return true;
			return false;
		}

		@Override
		public void freeze(GameState state, Identified thisObject)
		{
			this.color = Identity.fromCollection(this.color.evaluate(state, thisObject)).noTextChanges();
		}
	}

	public static final class ProhibitCasts extends StaticAbility
	{
		public ProhibitCasts(GameState state)
		{
			super(state, "Your opponents can't cast spells of the chosen color.");

			SetGenerator chosenColor = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));

			SimpleEventPattern castSpell = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			castSpell.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			castSpell.put(EventType.Parameter.OBJECT, new CastableOfColor(chosenColor));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castSpell));

			this.addEffectPart(part);

			this.getLinkManager().addLinkClass(ChooseAColor.class);
		}
	}

	public IonaShieldofEmeria(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// As Iona, Shield of Emeria enters the battlefield, choose a color.
		this.addAbility(new ChooseAColor(state));

		// Your opponents can't cast spells of the chosen color.
		this.addAbility(new ProhibitCasts(state));
	}
}
