package org.rnd.jmagic.engine;

/**
 * Represents instructions for the delayed creation of a Set. Evaluating a
 * SetGenerator produces a Set that will be different each evaluation, based on
 * the game state at evaluation time.
 * 
 * Classes extending this class may be singleton; that is, they may have no
 * parameters and never change. In this case, it is customary to keep a private
 * static instance of that class and return it whenever the instance() method is
 * called. Generators that are used "frequently" by the engine should use eager
 * instantiation for this instance; generators that are used "infrequently"
 * (e.g., only on a few specific cards) should use lazy instantiation.
 */
public abstract class SetGenerator
{
	/**
	 * This is the evaluate method that should be used in most cases.
	 * 
	 * @param game The context in which to evaluate this set generator
	 * @param thisObject The thing that is causing this evaluation (for example,
	 * a resolving spell or ability, or a player with shroud)
	 * @return The generated Set
	 */
	public final Set evaluate(Game game, Identified thisObject)
	{
		return this.evaluate(game.actualState, thisObject);
	}

	/**
	 * This evaluate method should only be called in one place outside the
	 * SetGenerator code and the code for its derived classes. The trigger code
	 * uses this method to evaluate SetGenerators in a previous context to
	 * handle look-back-in-time triggered abilities (see the "triggersOn" method
	 * in TriggeredAbility).
	 * 
	 * Classes that inherit from SetGenerator will call this to evaluate any
	 * "inner" SetGenerators, and they will override this method to define the
	 * semantics of evaluating themselves.
	 * 
	 * @param state The context in which to evaluate this set generator
	 * @param thisObject The thing that is causing this evaluation (for example,
	 * a resolving spell or ability, or a player with shroud)
	 * @return The generated Set
	 */
	public abstract Set evaluate(GameState state, Identified thisObject);

	/**
	 * Determines what color objects (or colors of mana objects) this set
	 * generator could resolve to.
	 * 
	 * @param game The context in which to evaluate this set generator
	 * @param thisObject The object that is asking for colors (for example, a
	 * resolving spell or ability)
	 * @param ignoreThese set generators to ignore when evaluating child set
	 * generators. this is important for preventing infinite loops between
	 * reflecting pools.
	 */
	public java.util.Set<ManaSymbol.ManaType> extractColors(Game game, GameObject thisObject, java.util.Set<SetGenerator> ignoreThese) throws NoSuchMethodException
	{
		throw new NoSuchMethodException("Incomplete extractColors method.  Failed to handle " + this.getClass().toString());
	}

	/**
	 * Determines if this set generator could resolve to a number greater than
	 * zero. Used to determine if a source would produce any mana before
	 * checking what colors it would produce.
	 * 
	 * @param game The context in which to evaluate this set generator
	 * @param thisObject The object to evaluate as the 'this' for generators
	 */
	public boolean isGreaterThanZero(Game game, GameObject thisObject) throws NoSuchMethodException
	{
		throw new NoSuchMethodException("Incomplete isGreaterThanZero method.  Failed to handle " + this.getClass().toString());
	}
}
