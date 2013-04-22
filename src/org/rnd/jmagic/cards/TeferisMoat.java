package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Teferi's Moat")
@Types({Type.ENCHANTMENT})
@ManaCost("3WU")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.SPECIAL), @Printings.Printed(ex = Expansion.INVASION, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class TeferisMoat extends Card
{
	public static final class TeferisMoatAbility0 extends StaticAbility
	{
		public TeferisMoatAbility0(GameState state)
		{
			super(state, "As Teferi's Moat enters the battlefield, choose a color.");

			this.getLinkManager().addLinkClass(TeferisMoatAbility1.class);

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "Choose a color");
			replacement.addPattern(asThisEntersTheBattlefield());

			SetGenerator originalEvent = replacement.replacedByThis();

			EventFactory factory = new EventFactory(EventType.PLAYER_CHOOSE, "Choose a color.");
			factory.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(originalEvent));
			factory.parameters.put(EventType.Parameter.CHOICE, Identity.instance(Color.allColors()));
			factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.COLOR, PlayerInterface.ChooseReason.CHOOSE_COLOR));
			factory.parameters.put(EventType.Parameter.OBJECT, This.instance());
			factory.setLink(this);
			replacement.addEffect(factory);

			this.addEffectPart(replacementEffectPart(replacement));

			this.canApply = NonEmpty.instance();
		}
	}

	public static final class TeferisMoatAbility1 extends StaticAbility
	{
		public TeferisMoatAbility1(GameState state)
		{
			super(state, "Creatures of the chosen color without flying can't attack you.");
			this.getLinkManager().addLinkClass(TeferisMoatAbility0.class);

			SetGenerator nonflyers = RelativeComplement.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			SetGenerator ofColor = HasColor.instance(ChosenFor.instance(LinkedTo.instance(This.instance())));
			SetGenerator nonflyersAttackingYou = Intersect.instance(nonflyers, ofColor, Attacking.instance(You.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(nonflyersAttackingYou));
			this.addEffectPart(part);
		}
	}

	public TeferisMoat(GameState state)
	{
		super(state);

		// As Teferi's Moat enters the battlefield, choose a color.
		this.addAbility(new TeferisMoatAbility0(state));

		// Creatures of the chosen color without flying can't attack you.
		this.addAbility(new TeferisMoatAbility1(state));
	}
}
